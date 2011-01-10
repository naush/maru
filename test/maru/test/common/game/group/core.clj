(ns maru.test.common.game.group.core
  (:use [maru.common.game.group.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest craft-a-group-with-color-and-stones
  (let [group (craft board/black [0 1])]
    (is (= board/black (:color group)))
    (is (= (set [0 1]) (:stones group)))))

(deftest find-one-neighbor-given-last-move
  (board/reset 5)
  (let [current-board (reduce #(board/play-white %1 %2) board/empty (list 5 6))]
    (is (= (set [5 6]) (find-neighboring-ally current-board 6 board/white ())))))

(deftest find-one-neighbor-in-any-direction
  (board/reset 5)
  (let [current-board (reduce #(board/play-white %1 %2) board/empty (list 1 6))]
    (is (= (set [1 6]) (find-neighboring-ally current-board 6 board/white ())))))

(deftest find-more-than-one-neighbor-given-move
  (board/reset 5)
  (let [current-board (reduce #(board/play-white %1 %2) board/empty (list 7 8 6))]
    (is (= (set [6 7 8]) (find-neighboring-ally current-board 6 board/white ())))))

(deftest find-neighbors-in-any-direction
  (board/reset 5)
  (let [current-board (reduce #(board/play-white %1 %2) board/empty (list 1 6 7))]
    (is (= (set [1 6 7]) (find-neighboring-ally current-board 6 board/white ())))))

(deftest find-ally-groups-for-last-move
  (let [last-move 2
	    current-board (reduce #(board/play-white %1 %2) board/empty (list 0 1 2))]
    (is (= {:color board/white :stones (set [0 1 2])} (find-ally current-board last-move board/white)))))

(deftest find-one-enemy-group-next-to-last-move
  (let [current-board (reduce #(board/play %1 (:pos %2) (:color %2)) board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)))]
    (is (= (set [{:color board/white :stones (set [0 1])}]) (find-enemy current-board 2 board/black)))))

(deftest find-enemy-groups-next-to-last-move
  (board/reset 5)
  (let [current-board (reduce #(board/play %1 (:pos %2) (:color %2)) board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)
          (stone/craft 3 board/white)))]
    (is (= (set [{:color board/white :stones (set [0 1])}
                 {:color board/white :stones (set [3])}]) (find-enemy current-board 2 board/black)))))

(deftest find-connected-enemy-groups-next-to-last-move
  (board/reset 5)
  (let [current-board (reduce #(board/play %1 (:pos %2) (:color %2)) board/empty
    (list (stone/craft 0 board/white)
          (stone/craft 1 board/white)
          (stone/craft 2 board/black)
          (stone/craft 6 board/white)
          (stone/craft 7 board/white)))]
    (is (= (set [{:color board/white :stones (set [0 1 6 7])}]) (find-enemy current-board 2 board/black)))))
