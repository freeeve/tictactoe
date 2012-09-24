(ns tictactoe.game
  (:use tictactoe.board)
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl]
            [clojurewerkz.neocons.rest.cypher :as cy]
            [clojurewerkz.neocons.rest.records :as records]))

(defn get-move 
  "gets the index of an empty position"
  [board p]
  (loop [i 0]
    (if (>= i (count board))
      nil
      (if (= (board i) "")
      i
      (recur (+ 1 i))))))

(defn get-opposite
  [p]
  (if (= p 'X) 
    'O 
    'X))

(defn get-board
  [node]
  (get-in node [:data :board]))

(defn board-to-str
  [board]
  (let [q "\""
        s (map #(str q % q) board)]
    (str "[" (clojure.string/join ", " s) "]" )))

(defn find-board
  [board n]
  (loop [i 0]
    (if (= i (count (keys permutation-map)))
      nil
      (let [permutation (nth (keys permutation-map) i)
            b (get-permutated-board board n permutation)
            data (cy/query (str "START n=node(*) WHERE n.board=" (board-to-str b) " RETURN n"))]
        (if (= 0 (count (:data data)))
          (recur (+ i 1))
          (let [n (records/instantiate-node-from (first (first (:data data))))]
            {:node n
             :board (get-board n)
             :permutation permutation}))))))

(defn solve-recurse
  [root p n]
  (let [board (get-board root)
        possible-moves (get-empty-spaces board)
        player p]
    (loop [i 0]
      (if (< i (count possible-moves))
        (let [newboard (set-board-value board (possible-moves i) player)
              foundboard (find-board newboard n)]
          (if (not= nil foundboard)
            (let [rel (nrl/create root (:node foundboard) :move)]
                ;(println (board-to-str newboard))
                ;(println "using existing board")
                (recur (+ i 1)))
            (let [move (nn/create {:board newboard :score (get-score newboard n) :n n})
                  rel (nrl/create root move :move)]
              (if (= 0 (get-score newboard n))
                (do
                  (println (print-board newboard n :normal))
                  (println "new board... recursing")
                  (solve-recurse move (get-opposite p) n)))
              (recur (+ i 1)))))))))

(defn solve
  "store all possible moves into the database."
  [n]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [root (nn/create {:board (empty-board n) :score 0 :n n})]
    (solve-recurse root 'X n)))

(defn play 
  "play a game of tictactoe on a board of size n"
  [n]
  (loop [move 0
         board (empty-board n)] 
    (let [empty-spaces (get-empty-spaces board)]
      (if (= (count empty-spaces) 0)
        (println "done!")
        (let [m1 (rand-nth empty-spaces)]
          (if (= m1 nil) 
            (println "game over!")
            (let [b (set-board-value board m1 'X)
              m2 (rand-nth (get-empty-spaces b))]
              (println (print-board b n :normal))
              (if (= m2 nil)
                (println "game over!")
                (let [b2 (set-board-value b m2 'O)]
                  (println (print-board b2 n :normal))
                  (recur (+ 2 move) b2))))))))))
