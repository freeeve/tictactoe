(ns tictactoe.board-test
  (:use clojure.test
        tictactoe.board))

(defn range-board 
  "returns a vector from 0 to n * n - 1, for testing"
  [n]
  (vec (range (* n n))))

(deftest print-normal
  (testing "print normal"
    (is (= (print-board (range-board 1) 1 :normal)
" 0
"))
    (is (= (print-board (range-board 2) 2 :normal)
" 0 1
 2 3
"))
    (is (= (print-board (range-board 3) 3 :normal)
" 0 1 2
 3 4 5
 6 7 8
"))
    (is (= (print-board (range-board 4) 4 :normal)
"  0  1  2  3
  4  5  6  7
  8  9 10 11
 12 13 14 15
"))
  )
)

(deftest print-perms-1
  (testing "print permutations for 1x1"
    (let [board (range-board 1)]
      (is (= (print-board board 1 :normal) " 0\n"))
      (is (= (print-board board 1 :rotate-left-90) " 0\n"))
      (is (= (print-board board 1 :rotate-left-180) " 0\n"))
      (is (= (print-board board 1 :rotate-left-270) " 0\n"))
      (is (= (print-board board 1 :reflect-on-d1) " 0\n"))
      (is (= (print-board board 1 :reflect-on-d2) " 0\n"))
      (is (= (print-board board 1 :reflect-vertical) " 0\n"))
      (is (= (print-board board 1 :reflect-horizontal) " 0\n"))
    )
  ))


(deftest print-perms-2
  (testing "print permutations for 2x2"
    (let [n 2 board (range-board n)]
      (is (= (print-board board n :normal) 
" 0 1
 2 3\n"))
      (is (= (print-board board n :rotate-left-90)
" 1 3
 0 2\n"))
      (is (= (print-board board n :rotate-left-180) 
" 3 2
 1 0\n"))
      (is (= (print-board board n :rotate-left-270) 
" 2 0
 3 1\n"))
      (is (= (print-board board n :reflect-on-d1) 
" 0 2
 1 3\n"))
      (is (= (print-board board n :reflect-on-d2)
" 3 1
 2 0\n"))
      (is (= (print-board board n :reflect-vertical)
" 1 0
 3 2\n"))
      (is (= (print-board board n :reflect-horizontal)
" 2 3
 0 1\n"))
    )))

(deftest print-perms-3
  (testing "print permutations for 3x3"
    (let [n 3 board (range-board n)]
      (is (= (print-board board n :normal)
" 0 1 2
 3 4 5
 6 7 8\n"))
      (is (= (print-board board n :rotate-left-90)
" 2 5 8
 1 4 7
 0 3 6\n"))
      (is (= (print-board board n :rotate-left-180)
" 8 7 6
 5 4 3
 2 1 0\n"))
      (is (= (print-board board n :rotate-left-270)
" 6 3 0
 7 4 1
 8 5 2\n"))
      (is (= (print-board board n :reflect-on-d1)
" 0 3 6
 1 4 7
 2 5 8\n"))
      (is (= (print-board board n :reflect-on-d2)
" 8 5 2
 7 4 1
 6 3 0\n"))
      (is (= (print-board board n :reflect-vertical)
" 2 1 0
 5 4 3
 8 7 6\n"))
      (is (= (print-board board n :reflect-horizontal)
" 6 7 8
 3 4 5
 0 1 2\n"))
    )))
