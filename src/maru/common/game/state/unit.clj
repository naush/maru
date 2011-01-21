(ns maru.common.game.state.unit
  (:use [maru.common.game.state.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:use [clojure.test]))

(deftest set-and-get-komi
  (set-komi 7.5)
  (is (= 7.5 komi)))

(deftest set-and-get-size
  (set-size 9)
  (is (= 9 size)))

(deftest set-and-get-board
  (set-board board/empty)
  (is (= board/empty board)))

(deftest append-one-move-to-log
  (empty-log)
  (logging 0)
  (is (= (list 0) log)))

(deftest append-two-moves-to-log
  (empty-log)
  (logging 0)
  (logging 1)
  (is (= (list 0 1) log)))
