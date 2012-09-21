(ns tictactoe.board)

(defn empty-board
  "returns an empty board (vector) of n * n."
  [n]
  (vec (repeat (* n n) "")))

(def permutation-map 
  {:normal             (fn [x y n] (+ x (* y n)))
   :rotate-left-90     (fn [x y n] (+ (- n (+ y 1)) (* x n)))
   :rotate-left-180    (fn [x y n] (+ (- n (+ x 1)) (* (- n (+ y 1)) n)))
   :rotate-left-270    (fn [x y n] (+ y (* (- n (+ x 1)) n)))
   :reflect-vertical   (fn [x y n] (+ (- n (+ x 1)) (* y n)))
   :reflect-horizontal (fn [x y n] (+ x (* (- n (+ y 1)) n)))
   :reflect-on-d1      (fn [x y n] (+ y (* x n)))
   :reflect-on-d2      (fn [x y n] (+ (- n (+ y 1)) (* (- n (+ x 1)) n)))
   })

(defn check-all 
  [board n p]
  (if (check-d1 board n p)
    true
    (if (check-d2 board n p)
      true
      (if (check-horizontals board n p)
        true
        (if (check-verticals board n p)
          true
          false)))))

(defn get-score
  [board n]
  (let [p 'X
        x-score (check-all board n p)
        opp 'O
        o-score (check-all board n opp)]))

(defn get-empty-spaces
  "returns a vector of the empty spaces within a board"
  [board]
  (loop [i 0
         empty []]
    (if (= (+ i 1) (count board))
      empty
      (if (= (board i) "")
        (recur (+ 1 i) (conj empty i))
        (recur (+ 1 i) empty)))))

(defn get-board-value
  "gets the value of a position on a board of n * n, after applying a permutation 
  function to the coordinates. defaults: n=2, permutation=:normal"
  ([board x y]
  (get-board-value board x y 3 :normal))
  ([board x y n]
  (get-board-value board x y n :normal))
  ([board x y n permutation]
  (board ((permutation permutation-map) x y n))))

(defn set-board-value
  "sets the value of a position on a board of n*n"
  [board i val]
  (assoc board i val))

(defn print-board [board n permutation]
  (loop [x 0
         y 0
         buf ""]
    (if (= y n) buf
      (if (= x n)
        (recur 0 (+ y 1) (str buf "\n"))
        (recur (+ 1 x) y (str buf (format (str "%" (+ (.length (str (* n n))) 1) "s") (get-board-value board x y n permutation))))))))
