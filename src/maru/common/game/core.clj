(ns maru.common.game.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.game.rule.core :as rule]))

(defn play [state x y color]
  (let [point (board/to-point x y (:size state))
        current-board (board/set-stone (:board state) point color)
        enemies (group/find-enemies current-board point color (:size state))
        captures (group/find-captures enemies)]
    (if (= 1 (count captures))
      (assoc state :board (board/remove-stones current-board captures) :ko (first captures))
      (assoc state :board (board/remove-stones current-board captures)))))
