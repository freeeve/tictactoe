(ns tictactoe.game)

(defn get-move [board p]
  (filter "" ))

(defn make-move [board x y p]
  (assoc board p))

(defn play 
  "play a game of tictactoe on a board of size n"
  [n]
  (loop [move 0
         board (vec (repeat (* n n) ""))] 
    (let [b (get-move board 'O)
          b2 (get-move b 'X)]
      (recur (+ 2 move) b2))))

