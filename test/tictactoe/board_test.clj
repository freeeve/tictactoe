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
           (str " 0\n")))
    (is (= (print-board (range-board 2) 2 :normal) 
           (str " 0 1\n"
                " 2 3\n")))
    (is (= (print-board (range-board 3) 3 :normal) 
           (str " 0 1 2\n"
                " 3 4 5\n"
                " 6 7 8\n")))
    (is (= (print-board (range-board 4) 4 :normal) 
           (str "  0  1  2  3\n"
                "  4  5  6  7\n"
                "  8  9 10 11\n"
                " 12 13 14 15\n")))
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
             (str " 0 1\n"
                  " 2 3\n")))
      (is (= (print-board board n :rotate-left-90) 
             (str " 1 3\n"
                  " 0 2\n")))
      (is (= (print-board board n :rotate-left-180) 
             (str " 3 2\n"
                  " 1 0\n")))
      (is (= (print-board board n :rotate-left-270) 
             (str " 2 0\n"
                  " 3 1\n")))
      (is (= (print-board board n :reflect-on-d1) 
             (str " 0 2\n"
                  " 1 3\n")))
      (is (= (print-board board n :reflect-on-d2) 
             (str " 3 1\n"
                  " 2 0\n")))
      (is (= (print-board board n :reflect-vertical) 
             (str " 1 0\n"
                  " 3 2\n")))
      (is (= (print-board board n :reflect-horizontal) 
             (str " 2 3\n"
                  " 0 1\n")))
    )))

(deftest print-perms-3
  (testing "print permutations for 3x3"
    (let [n 3 board (range-board n)]
      (is (= (print-board board n :normal) 
             (str " 0 1 2\n"  
                  " 3 4 5\n"
                  " 6 7 8\n")))
      (is (= (print-board board n :rotate-left-90) 
             (str " 2 5 8\n"
                  " 1 4 7\n"
                  " 0 3 6\n")))
      (is (= (print-board board n :rotate-left-180) 
             (str " 8 7 6\n"
                  " 5 4 3\n"
                  " 2 1 0\n")))
      (is (= (print-board board n :rotate-left-270) 
             (str " 6 3 0\n"
                  " 7 4 1\n"
                  " 8 5 2\n")))
      (is (= (print-board board n :reflect-on-d1) 
             (str " 0 3 6\n"
                  " 1 4 7\n"
                  " 2 5 8\n")))
      (is (= (print-board board n :reflect-on-d2) 
             (str " 8 5 2\n"
                  " 7 4 1\n"
                  " 6 3 0\n")))
      (is (= (print-board board n :reflect-vertical)
             (str " 2 1 0\n"
                  " 5 4 3\n"
                  " 8 7 6\n")))
      (is (= (print-board board n :reflect-horizontal)
             (str " 6 7 8\n"
                  " 3 4 5\n"
                  " 0 1 2\n")))
    )))

(deftest test-check-d1-3
  (testing "testing check-d1 n=3"
    (let [n 3 
          board (empty-board n)]
      (is (= (check-d1 board n 'X) false))
      (let [board (set-board-value-x-y board 0 0 n 'X)]
        (is (= (check-d1 board n 'X) false))
        (let [board (set-board-value-x-y board 1 1 n 'X)]
          (is (= (check-d1 board n 'X) false))
          (let [board (set-board-value-x-y board 2 2 n 'X)]
            (is (= (check-d1 board n 'X) true))))))))

(deftest test-check-d2-3
  (testing "testing check-d2 n=3"
    (let [n 3 
          board (empty-board n)]
      (is (= (check-d2 board n 'X) false))
      (let [board (set-board-value-x-y board 0 2 n 'X)]
        (is (= (check-d2 board n 'X) false))
        (let [board (set-board-value-x-y board 1 1 n 'X)]
          (is (= (check-d2 board n 'X) false))
          (let [board (set-board-value-x-y board 2 0 n 'X)]
            (is (= (check-d2 board n 'X) true))))))))

(deftest test-check-horizontals
  (testing "testing check-horizontals n=3"
    (let [n 3 
          board (empty-board n)]
      (is (= (check-horizontals board n 'X) false))
      (let [board (set-board-value-x-y board 0 0 n 'X)]
        (is (= (check-horizontals board n 'X) false))
        (let [board (set-board-value-x-y board 1 0 n 'X)]
          (is (= (check-horizontals board n 'X) false))
          (let [board (set-board-value-x-y board 2 0 n 'X)]
            (is (= (check-horizontals board n 'X) true))))))))

(deftest test-check-verticals
  (testing "testing check-verticals n=3"
    (let [n 3 
          board (empty-board n)]
      (is (= (check-verticals board n 'X) false))
      (let [board (set-board-value-x-y board 0 0 n 'X)]
        (is (= (check-verticals board n 'X) false))
        (let [board (set-board-value-x-y board 0 1 n 'X)]
          (is (= (check-verticals board n 'X) false))
          (let [board (set-board-value-x-y board 0 2 n 'X)]
            (is (= (check-verticals board n 'X) true))))))))

(deftest test-get-empty-spaces
  (testing "testing get-empty-spaces"
    (let [n 3
          board (empty-board n)]
      (is (= (* n n) (count (get-empty-spaces board)))) 
      )))
