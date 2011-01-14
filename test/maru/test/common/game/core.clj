(ns maru.test.common.game.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.rule.core :as rule])
  (:use [maru.common.game.core] :reload)
  (:use [clojure.test]))

(deftest set-game-board-on-state
  (state/set-size 3)
  (play state/board 0 0 board/black)
  (is (= [1 0 0 0 0 0 0 0 0] state/board)))

(deftest make-multiple-moves-on-state-board
  (state/set-size 3)
  (play state/board 0 0 board/black)
  (play state/board 1 0 board/white)
  (play state/board 2 0 board/black)
  (is (= [1 2 1 0 0 0 0 0 0] state/board)))

(deftest capture-stones
  (state/set-size 3)
  (play state/board 0 0 board/black)
  (play state/board 1 0 board/white)
  (play state/board 0 1 board/white)
  (is (= [0 2 0 2 0 0 0 0 0] state/board)))
