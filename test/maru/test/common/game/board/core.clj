(ns maru.test.common.game.board.core
  (:refer-clojure :exclude [empty])
  (:use [maru.common.game.board.core] :reload)
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest convert-x-min-y-min-to-point
  (state/set-size 9)
  (is (= 0 (to-point 0 0))))

(deftest convert-x-one-y-min-to-point
  (state/set-size 9)
  (is (= 1 (to-point 1 0))))

(deftest convert-x-max-y-min-to-point
  (state/set-size 9)
  (is (= 8 (to-point 8 0))))

(deftest convert-x-max-y-one-to-point
  (state/set-size 9)
  (is (= 17 (to-point 8 1))))

(deftest convert-x-max-y-max-to-point
  (state/set-size 9)
  (is (= 80 (to-point 8 8))))

(deftest convert-point-min-to-x
  (state/set-size 9)
  (is (= 0 (to-x 0))))

(deftest convert-point-one-to-x
  (state/set-size 9)
  (is (= 1 (to-x 1))))

(deftest convert-point-max-to-x
  (state/set-size 9)
  (is (= 8 (to-x 8))))

(deftest convert-point-max-plus-one-to-x
  (state/set-size 9)
  (is (= 0 (to-x 9))))

(deftest convert-point-max-times-max-to-x
  (state/set-size 9)
  (is (= 8 (to-x 80))))

(deftest convert-point-min-to-y
  (state/set-size 9)
  (is (= 0 (to-y 0))))

(deftest convert-point-one-to-y
  (state/set-size 9)
  (is (= 0 (to-y 1))))

(deftest convert-point-max-to-y
  (state/set-size 9)
  (is (= 0 (to-y 8))))

(deftest convert-point-max-plus-one-to-y
  (state/set-size 9)
  (is (= 1 (to-y 9))))

(deftest convert-point-max-times-max-to-y
  (state/set-size 9)
  (is (= 8 (to-y 80))))

(deftest set-stone-at-point-with-black
  (is (= black (nth (set-stone empty 0 black) 0))))

(deftest set-stone-at-point-with-white
  (is (= white (nth (set-white empty 0) 0))))

(deftest set-multiple-stones
  (let [current-board (set-stones empty
    (list (stone/craft 0 white)
          (stone/craft 1 white)))]
    (is (= white (nth current-board 0)))
    (is (= white (nth current-board 1)))))

(deftest remove-stone-at-point
  (let [current-board (set-stone empty 0 black)
        removed-board (remove-stone current-board 0)]
    (is (= open (nth removed-board 0)))))

(deftest remove-given-stones
  (let [stone-a (stone/craft 0 white)
        stone-b (stone/craft 1 white)
        current-board (set-stones empty
          (list stone-a stone-b))
        removed-board (remove-stones current-board [0 1])]
    (is (= open (nth removed-board 0)))
    (is (= open (nth removed-board 1)))))

(deftest check-color-at-point
  (is (= white (color (set-white empty 0) 0))))

(deftest find-neighbors-at-upper-left-corner
  (state/set-size 5)
  (is (= (set [1 5]) (find-neighbors 0))))

(deftest find-neighbors-at-lower-right-corner
  (state/set-size 5)
  (is (= (set [19 23]) (find-neighbors 24))))

(deftest find-four-neighbors
  (state/set-size 5)
  (is (= (set [1 5 7 11]) (find-neighbors 6))))

(deftest check-negative-against-out-of-bound
  (state/set-size 5)
  (is (= true (out-of-bound -1 0)))
  (is (= true (out-of-bound 0 -1))))

(deftest check-max-plus-one-against-out-of-bound
  (state/set-size 5)
  (is (= true (out-of-bound 5 0)))
  (is (= true (out-of-bound 0 5))))

(deftest check-min-against-out-of-bound
  (state/set-size 5)
  (is (= false (out-of-bound 0 0))))

(deftest check-max-against-out-of-bound
  (state/set-size 5)
  (is (= false (out-of-bound 4 4))))
