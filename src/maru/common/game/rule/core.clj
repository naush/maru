(ns maru.common.game.rule.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state]))

(defn dead? [group] (= (count (:liberties group)) 0))

(defn alive? [group] (not (dead? group)))

(defn suicide? [board x y color]
  (let [point (board/to-point x y)
        current-board (board/set-stone board point color)
        enemies (group/find-enemies current-board point color)
        ally (group/find-ally current-board point color)]
    (and (reduce #(and %1 (> (count (:liberties %2)) 0)) true enemies)
         (= (count (:liberties ally)) 0))))

(defn legal? [board x y color]
  (and (not (= state/ko (board/to-point x y)))
       (not (board/out-of-bound x y))
       (board/open? board x y)
       (not (suicide? board x y color))))

(defn all-legal-moves [board color]
  (filter #(legal? board (board/to-x %) (board/to-y %) color) (range 0 (* state/size state/size))))