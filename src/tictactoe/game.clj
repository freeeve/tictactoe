(ns tictactoe.game
  (:use tictactoe.board)
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn get-move 
  "gets the index of an empty position"
  [board p]
  (loop [i 0]
    (if (>= i (count board))
      nil
      (if (= (board i) "")
      i
      (recur (+ 1 i))))))

(defn solve
  "store all possible moves into the database."
  [n]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [root (nn/create {:board (empty-board n)})]
    (solve-recurse root 'X)))

(defn solve-recurse
  [root p]
  (let [board (:board root)
        possible-moves (get-empty-spaces board)
        player p]
      (loop [i 0]
        (if (< i (count possible-moves))
          (let [move (nn/create {:board (set-board-value board (possible-moves i) player)})
                rel (nrl/create root move :move {:score (get-score (:board move))})]
            (solve-recurse move (get-opposite p))
            (recur (+ i 1)))))))

(defn get-opposite
  [p]
  (if (= p 'X) 
    'O 
    'X))

(defn play 
  "play a game of tictactoe on a board of size n"
  [n]
  (loop [move 0
         board (empty-board n)] 
    (let [m1 (rand-nth (get-empty-spaces board))]
      (if (= m1 nil) 
        (println "game over!")
        (let [b (set-board-value board m1 'X)
              m2 (rand-nth (get-empty-spaces b))]
          (println (print-board b n :normal))
          (if (= m2 nil)
            (println "game over!")
            (let [b2 (set-board-value b m2 'O)]
              (println (print-board b2 n :normal))
              (recur (+ 2 move) b2))))))))
