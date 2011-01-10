(ns maru.test.common.game.info.core
  (:use [maru.common.game.info.core] :reload)
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
