(ns maru.common.game.log.core
  (:use [maru.common.utility.core :only [insert-last]]))

(defn ging [log move] (insert-last log move))
