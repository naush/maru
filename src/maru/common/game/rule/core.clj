(ns maru.common.game.rule.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state]))

(defn dead? [group] (= (count (:liberties group)) 0))

(defn alive? [group] (not (dead? group)))

(defn suicide? [board x y color size]
  (let [point (board/to-point x y size)
        current-board (board/set-stone board point color)
        enemies (group/find-enemies current-board point color size)
        ally (group/find-ally current-board point color size)]
    (and (reduce #(and %1 (> (count (:liberties %2)) 0)) true enemies)
         (= (count (:liberties ally)) 0))))

(defn legal? [board x y color size ko]
  (and (not (= ko (board/to-point x y size)))
       (not (board/out-of-bound x y size))
       (board/open? board x y size)
       (not (suicide? board x y color size))))

(defn all-legal-moves [board color size ko]
  (filter #(legal? board
	           (board/to-x % size)
             (board/to-y % size) color size ko)
             (range 0 (* size size))))
