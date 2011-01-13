(ns maru.test.common.game.core
  (:use [maru.common.game.core] :reload)
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.info.core :as info])
  (:require [maru.common.game.stone.core :as stone])
  (:use [clojure.test]))

(deftest return-false-when-move-is-ko
  (board/reset 5)
  (info/set-ko 0)
  (is (= false (legal? board/empty 0 0 board/black))))
