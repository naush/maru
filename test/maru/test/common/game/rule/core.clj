(ns maru.test.common.game.rule.core
  (:use [maru.common.game.rule.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest return-true-when-move-is-legal
  (state/set-size 5)
  (state/set-ko -1)
  (is (= true (legal? board/empty 0 0 board/black))))

(deftest return-false-when-move-is-ko
  (state/set-size 5)
  (state/set-ko 0)
  (is (= false (legal? board/empty 0 0 board/black))))

(deftest return-false-when-move-is-out-of-bound
  (state/set-size 5)
  (is (= false (legal? board/empty -1 -1 board/black))))

(deftest return-false-when-move-is-not-empty
  (state/set-size 5)
  (let [current-board (board/set-stone board/empty 0 board/black)]
    (is (= false (legal? current-board 0 0 board/black)))))

(deftest return-true-when-enemies-groups-have-zero-liberty
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/black)))]
    (is (= true (legal? current-board 0 1 board/black)))))

(deftest return-false-when-suicide
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= true (suicide? current-board 1 0 board/black)))))

(deftest legal-returns-false-when-suicide
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/white)))]
    (is (= false (legal? current-board 1 0 board/black)))))

(deftest return-true-when-given-group-is-dead
  (is (= true (dead? (group/craft board/white () ())))))

(deftest return-false-when-given-group-is-not-dead
  (is (= false (dead? (group/craft board/white () (list (stone/craft 0 board/white)))))))

(deftest return-all-legal-moves-on-empty-board
  (state/set-size 5)
  (state/set-ko -999)
  (is (= (range 0 (* state/size state/size)) (all-legal-moves board/empty board/white))))

(deftest return-nothing-on-full-board
  (is (= () (all-legal-moves (vec (repeat 25 board/black)) board/white))))
