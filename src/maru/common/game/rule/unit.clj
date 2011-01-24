(ns maru.common.game.rule.unit
  (:use [maru.common.game.rule.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.color.core :as color])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest return-true-when-move-is-legal
  (is (= true (legal? color/black 0 0
	                    (hash-map :size 5 :ko -999 :board (board/empty 5))))))

(deftest return-false-when-move-is-ko
  (is (= false (legal? color/black 0 0
	                    (hash-map :size 5 :ko 0 :board (board/empty 5))))))

(deftest return-false-when-move-is-out-of-bound
  (is (= false (legal? color/black -1 -1
	                    (hash-map :size 5 :ko -999 :board (board/empty 5))))))

(deftest return-false-when-move-is-not-empty
  (let [current-board (board/set-stone (board/empty 5) 0 color/black)]
    (is (= false (legal? color/black 0 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-enemies-groups-have-zero-liberty
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 1 color/black)))]
    (is (= true (legal? color/black 0 1
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest legal-returns-false-when-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 2 color/white)
          (stone/craft 6 color/white)))]
    (is (= false (legal? color/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 2 color/white)
          (stone/craft 6 color/white)))]
    (is (= true (suicide? color/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-one-liberty-left
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 3 color/white)
          (stone/craft 6 color/white)))]
    (is (= false (suicide? color/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-one-liberty-left-after-connected
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 2 color/black)
          (stone/craft 3 color/white)
          (stone/craft 6 color/white)))]
    (is (= false (suicide? color/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-surrounded-by-ally
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 2 color/white)
          (stone/craft 6 color/white)))]
    (is (= false (suicide? color/white 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-surrounded-by-ally-and-one-enemy
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 1 color/black)
          (stone/craft 5 color/white)
          (stone/craft 7 color/white)
          (stone/craft 11 color/white)))]
    (is (= false (suicide? color/black 1 1
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest legal-returns-true-when-not-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 color/white)
          (stone/craft 5 color/black)
          (stone/craft 2 color/white)
          (stone/craft 6 color/white)))]
    (is (= true (legal? color/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-given-group-is-dead
  (is (= true (dead? (group/craft color/white () ())))))

(deftest return-false-when-given-group-is-not-dead
  (is (= false (dead? (group/craft color/white () (list (stone/craft 0 color/white)))))))

(deftest return-all-legal-moves-on-empty-board
  (is (= (range 0 (* 5 5)) (all-legal-moves color/white
	                           (hash-map :size 5 :ko -999 :board (vec (repeat 25 color/open)))))))

(deftest return-nothing-on-full-board
  (is (= () (all-legal-moves color/white
	                           (hash-map :size 5 :ko -999 :board (vec (repeat 25 color/black)))))))
