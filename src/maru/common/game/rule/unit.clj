(ns maru.common.game.rule.unit
  (:use [maru.common.game.rule.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest return-true-when-move-is-legal
  (is (= true (legal? board/black 0 0
	                    (hash-map :size 5 :ko -999 :board (board/empty 5))))))

(deftest return-false-when-move-is-ko
  (is (= false (legal? board/black 0 0
	                    (hash-map :size 5 :ko 0 :board (board/empty 5))))))

(deftest return-false-when-move-is-out-of-bound
  (is (= false (legal? board/black -1 -1
	                    (hash-map :size 5 :ko -999 :board (board/empty 5))))))

(deftest return-false-when-move-is-not-empty
  (let [current-board (board/set-stone (board/empty 5) 0 board/black)]
    (is (= false (legal? board/black 0 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-enemies-groups-have-zero-liberty
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/black)))]
    (is (= true (legal? board/black 0 1
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest legal-returns-false-when-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= false (legal? board/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= true (suicide? board/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-one-liberty-left
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 3 board/white)
          (stone/craft 6 board/white)))]
    (is (= false (suicide? board/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-one-liberty-left-after-connected
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/black)
          (stone/craft 3 board/white)
          (stone/craft 6 board/white)))]
    (is (= false (suicide? board/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-surrounded-by-ally
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= false (suicide? board/white 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-false-when-surrounded-by-ally-and-one-enemy
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 1 board/black)
          (stone/craft 5 board/white)
          (stone/craft 7 board/white)
          (stone/craft 11 board/white)))]
    (is (= false (suicide? board/black 1 1
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest legal-returns-true-when-not-suicide
  (let [current-board (board/set-stones (board/empty 5)
    (list (stone/craft 0 board/white)
          (stone/craft 5 board/black)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= true (legal? board/black 1 0
	                    (hash-map :size 5 :ko -999 :board current-board))))))

(deftest return-true-when-given-group-is-dead
  (is (= true (dead? (group/craft board/white () ())))))

(deftest return-false-when-given-group-is-not-dead
  (is (= false (dead? (group/craft board/white () (list (stone/craft 0 board/white)))))))

(deftest return-all-legal-moves-on-empty-board
  (is (= (range 0 (* 5 5)) (all-legal-moves board/white
	                           (hash-map :size 5 :ko -999 :board (vec (repeat 25 board/open)))))))

(deftest return-nothing-on-full-board
  (is (= () (all-legal-moves board/white
	                           (hash-map :size 5 :ko -999 :board (vec (repeat 25 board/black)))))))
