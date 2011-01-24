(ns maru.common.game.unit
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.rule.core :as rule])
  (:use [maru.common.game.core] :reload)
  (:use [clojure.test]))

(deftest return-state-with-current-board
  (let [state (play (hash-map :board (vec (repeat (* 3 3) 0)) :size 3) 0 0 board/black)]
  (is (= [1 0 0 0 0 0 0 0 0] (:board state)))))

(comment

(deftest make-multiple-moves-on-state-board
  (play state/board 0 0 board/black 3)
  (play state/board 1 0 board/white 3)
  (play state/board 2 0 board/black 3)
  (is (= [1 2 1 0 0 0 0 0 0] state/board)))

(deftest capture-stones
  (play state/board 0 0 board/black 3)
  (play state/board 1 0 board/white 3)
  (play state/board 0 1 board/white 3)
  (is (= [0 2 0 2 0 0 0 0 0] state/board)))

)
