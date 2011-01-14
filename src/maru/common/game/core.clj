(ns maru.common.game.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.rule.core :as rule]))

(defn play [board x y color]
  (let [point (board/to-point x y)
        current-board (board/set-stone board point color)
        enemies (group/find-enemies current-board point color)
        captures (group/find-captures enemies)]
    (if (= 1 (count captures)) (state/set-ko (first captures)) ())
    (state/set-board (board/remove-stones current-board captures))))
