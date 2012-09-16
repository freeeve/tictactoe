(ns tictactoe.board)

(defn empty-board
  "returns an empty board (vector) of n * n."
  [n]
  (vec (repeat (* n n) "")))

(defn get-board-value-with-permutation
  "gets the value of a position on a board of n * n, after applying a permutation 
  function to the coordinates"
  [board x y n permutation]
  (board ((permutation permutation-map) x y n)))

(def permutation-map 
  {:normal (fn [x y n] (+ x (* y n)))
   :rotate-left-90 (fn [x y n] (+ (- n (+ y 1)) (* x n)))
   :rotate-left-270 (fn [x y n] (+ y (* (- n (+ x 1)) n)))
   :reflect-on-d2 (fn [x y n] (+ (- n (+ y 1)) (* (- n (+ x 1)) n)))
   })

(defn print-board [board n permutation]
  (loop [x 0
         y 0]
    (if (= y n) (println)
      (if (= x n)
        (do 
          (println)
          (recur 0 (+ y 1)))
      (do
        (print (format (str "%" (+ (.length (str (* n n))) 1) "s") (get-board-value-with-permutation board x y n permutation)))
        (recur (+ 1 x) y))))))
