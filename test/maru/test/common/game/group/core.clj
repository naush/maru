(ns maru.test.common.game.group.core
  (:use [maru.common.game.group.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest craft-a-group-with-color-stones-and-liberties
  (state/set-size 5)
  (let [group (craft board/black [0 1] [2 5 6])]
    (is (= board/black (:color group)))
    (is (= (set [0 1]) (:stones group)))
    (is (= (set [2 5 6]) (:liberties group)))))

(deftest find-no-neighbor-given-last-move
  (state/set-size 5)
  (let [current-board (reduce #(board/set-white %1 %2) board/empty (list 5))]
    (is (= (set [5]) (find-ally-recursive current-board 5 board/white ())))))

(deftest find-one-neighbor-given-last-move
  (state/set-size 5)
  (let [current-board (reduce #(board/set-white %1 %2) board/empty (list 5 6))]
    (is (= (set [5 6]) (find-ally-recursive current-board 6 board/white ())))))

(deftest find-one-neighbor-in-any-direction
  (state/set-size 5)
  (let [current-board (reduce #(board/set-white %1 %2) board/empty (list 1 6))]
    (is (= (set [1 6]) (find-ally-recursive current-board 6 board/white ())))))

(deftest find-more-than-one-neighbor-given-move
  (state/set-size 5)
  (let [current-board (reduce #(board/set-white %1 %2) board/empty (list 7 8 6))]
    (is (= (set [6 7 8]) (find-ally-recursive current-board 6 board/white ())))))

(deftest find-neighbors-in-any-direction
  (state/set-size 5)
  (let [current-board (reduce #(board/set-white %1 %2) board/empty (list 1 6 7))]
    (is (= (set [1 6 7]) (find-ally-recursive current-board 6 board/white ())))))

(deftest find-no-ally-group-for-last-move
  (let [last-move 0
	    current-board (reduce #(board/set-white %1 %2) board/empty (list 0))]
    (is (= {:color board/white :stones (set [0]) :liberties (set [1 5])} (find-ally current-board last-move board/white)))))

(deftest find-ally-group-for-last-move
  (let [last-move 2
	    current-board (reduce #(board/set-white %1 %2) board/empty (list 0 1 2))]
    (is (= {:color board/white :stones (set [0 1 2]) :liberties (set [3 5 6 7])} (find-ally current-board last-move board/white)))))

(deftest find-one-enemies-group-next-to-last-move
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)))]
    (is (= (set [{:color board/white :stones (set [0 1]) :liberties (set [5 6])}]) (find-enemies current-board 2 board/black)))))

(deftest find-enemies-groups-next-to-last-move
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)
          (stone/craft 3 board/white)))]
    (is (= (set [{:color board/white :stones (set [0 1]) :liberties (set [5 6])}
                 {:color board/white :stones (set [3]) :liberties (set [4 8])}]) (find-enemies current-board 2 board/black)))))

(deftest find-connected-enemies-groups-next-to-last-move
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)
          (stone/craft 6 board/white)
          (stone/craft 7 board/white)))]
    (is (= (set [{:color board/white :stones (set [0 1 6 7]) :liberties (set [5 8 11 12])}]) (find-enemies current-board 2 board/black)))))

(deftest find-dead-enemies
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
          (list (stone/craft 0 board/white)
                (stone/craft 1 board/black)
                (stone/craft 5 board/black)))
        enemies (find-enemies current-board 1 board/black)
        captures (find-captures enemies)]
    (is (= (set [0]) captures))))

(deftest find-liberty-given-one-stone-and-board
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)))]
  (is (= (set [1 5]) (find-liberty current-board (set [0]))))))

(deftest find-liberty-given-one-ally-stone-one-adjacent-enemies-stone
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/black)))]
  (is (= (set [5]) (find-liberty current-board (set [0]))))))

(deftest find-liberty-given-stones
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 5 board/white)
          (stone/craft 6 board/white)
          (stone/craft 7 board/black)))]
  (is (= (set [0 1 10 11]) (find-liberty current-board (set [5 6]))))))

(deftest find-immediate-liberty-given-point
  (state/set-size 5)
  (is (= (list 1 5 7 11) (find-liberty-helper board/empty 6))))

(deftest find-immediate-liberty-given-point-exclude-out-of-bounds
  (state/set-size 5)
  (is (= (list 1 5) (find-liberty-helper board/empty 0))))

(deftest find-immediate-liberty-given-point-exclude-occupied
  (state/set-size 5)
  (let [current-board (board/set-stones board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 2 board/white)
          (stone/craft 6 board/black)))]
  (is (= () (find-liberty-helper current-board 1)))))
