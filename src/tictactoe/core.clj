(ns tictactoe.core
  (:require [tictactoe.game :as game]))

(defn -main
  "I play tic-tac-toe."
  [& args]
  (println "Starting game...")
  (game/play))
