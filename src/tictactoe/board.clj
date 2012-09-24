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

(defn get-board-value
  "gets the value of a position on a board of n * n, after applying a permutation 
  function to the coordinates. defaults: n=2, permutation=:normal"
  ([board x y]
  (get-board-value board x y 3 :normal))
  ([board x y n]
  (get-board-value board x y n :normal))
  ([board x y n permutation]
  (board ((permutation permutation-map) x y n))))

(defn get-permutated-board
  "gets a board in a particular permutation."
  [board n permutation]
  (loop [x 0 
         y 0
         newboard []]
    (if (= y n)
      newboard
      (if (= x n)
        (recur 0 (+ 1 y) newboard)
        (recur (+ 1 x) y (conj newboard (get-board-value board x y n permutation)))))))

(defn check-d1
  [board n p]
  (loop [i 0] 
    (if (= i n)
      true
      (if (and (< i n) (not= p (get-board-value board i i n)))
        false
        (recur (+ i 1))))))

(defn check-d2
  [board n p]
  (loop [i 0] 
    (if (= i n)
      true
      (if (and (< i n) (not= p (get-board-value board (- n (+ i 1)) i n)))
        false
        (recur (+ i 1))))))

(defn check-row
  [board row n p]
  (loop [x 0]
    (if (= x n)
      true
      (if (= p (get-board-value board x row n))
        (recur (+ 1 x))
        false))))

(defn check-horizontals
  [board n p]
  (loop [row 0]
    (if (= row n)
      false 
      (if (= true (check-row board row n p))
        true
        (recur (+ 1 row))))))
        
(defn check-column
  [board col n p]
  (loop [y 0]
    (if (= y n)
      true
      (if (= p (get-board-value board col y n))
        (recur (+ 1 y))
        false))))

(defn check-verticals
  [board n p]
  (loop [col 0]
    (if (= col n)
      false 
      (if (= true (check-column board col n p))
        true
        (recur (+ 1 col))))))
        
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
  (if (= true (check-all board n 'X))
    1
    (if (= true (check-all board n 'O))
      -1
      0)))

(defn get-empty-spaces
  "returns a vector of the empty spaces within a board"
  [board]
  (loop [i 0
         empty []]
    (if (= i (count board))
      empty
      (if (= (board i) "")
        (recur (+ 1 i) (conj empty i))
        (recur (+ 1 i) empty)))))

(defn set-board-value
  "sets the value of a position on a board of n*n"
  [board i val]
  (assoc board i val))

(defn set-board-value-x-y
  "sets the value of a position on a board of n*n"
  [board x y n val]
  (assoc board ((permutation-map :normal) x y n) val))

(defn print-board [board n permutation]
  (loop [x 0
         y 0
         buf ""]
    (if (= y n) buf
      (if (= x n)
        (recur 0 (+ y 1) (str buf "\n"))
        (recur (+ 1 x) y (str buf (format (str "%" (+ (.length (str (* n n))) 1) "s") (get-board-value board x y n permutation))))))))
