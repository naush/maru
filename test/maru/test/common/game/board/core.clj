(ns maru.test.common.game.board.core
  (:refer-clojure :exclude [empty])
  (:use [maru.common.game.board.core] :reload)
  (:use [clojure.test]))

(deftest convert-x-min-y-min-to-pos
  (reset 9)
  (is (= 0 (to-pos 0 0))))

(deftest convert-x-one-y-min-to-pos
  (reset 9)
  (is (= 1 (to-pos 1 0))))

(deftest convert-x-max-y-min-to-pos
  (reset 9)
  (is (= 8 (to-pos 8 0))))

(deftest convert-x-max-y-one-to-pos
  (reset 9)
  (is (= 17 (to-pos 8 1))))

(deftest convert-x-max-y-max-to-pos
  (reset 9)
  (is (= 80 (to-pos 8 8))))

(deftest convert-pos-min-to-x
  (reset 9)
  (is (= 0 (to-x 0))))

(deftest convert-pos-one-to-x
  (reset 9)
  (is (= 1 (to-x 1))))

(deftest convert-pos-max-to-x
  (reset 9)
  (is (= 8 (to-x 8))))

(deftest convert-pos-max-plus-one-to-x
  (reset 9)
  (is (= 0 (to-x 9))))

(deftest convert-pos-max-times-max-to-x
  (reset 9)
  (is (= 8 (to-x 80))))

(deftest convert-pos-min-to-y
  (reset 9)
  (is (= 0 (to-y 0))))

(deftest convert-pos-one-to-y
  (reset 9)
  (is (= 0 (to-y 1))))

(deftest convert-pos-max-to-y
  (reset 9)
  (is (= 0 (to-y 8))))

(deftest convert-pos-max-plus-one-to-y
  (reset 9)
  (is (= 1 (to-y 9))))

(deftest convert-pos-max-times-max-to-y
  (reset 9)
  (is (= 8 (to-y 80))))

(deftest set-stone-at-pos-with-black
  (is (= black (nth (set-stone empty 0 black) 0))))

(deftest set-white-stone-at-pos
  (is (= white (nth (set-white empty 0) 0))))

(deftest set-multiple-stones
  (let [current-board (reduce #(set-white %1 %2) empty '(0 1))]
    (is (= 2 (nth current-board 0)))
    (is (= 2 (nth current-board 1)))))

(deftest reset-board-with-size-nine
  (reset 9)
  (is (= 81 (count empty))))

(deftest check-color-at-pos
  (is (= white (color (set-white empty 0) 0))))

(deftest convert-string-to-color
  (is (= white (string-to-color "W")))
  (is (= white (string-to-color "w")))
  (is (= black (string-to-color "B")))
  (is (= black (string-to-color "b"))))

(deftest find-neighbors-at-upper-left-corner
  (reset 5)
  (is (= (set [1 5]) (find-neighbors 0))))

(deftest find-neighbors-at-lower-right-corner
  (reset 5)
  (is (= (set [19 23]) (find-neighbors 24))))

(deftest find-four-neighbors
  (reset 5)
  (is (= (set [1 5 7 11]) (find-neighbors 6))))
