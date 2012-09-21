(ns tictactoe.core
  (:use tictactoe.game))

(defn -main
  "I play tic-tac-toe."
  [& args]
  (println "Starting game...")
  (play 3))
