(ns maru.ghost.confucius.gtp
  (:require [maru.ghost.confucius.core :as ghost] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:gen-class))

(gtp/make-name-command #(ghost/name))
(gtp/make-version-command #(ghost/version))
(gtp/make-komi-command #(ghost/komi (first %1)))
(gtp/make-boardsize-command #(ghost/boardsize (first %1)))
(gtp/make-play-command #(ghost/play (first %1) (second %1)))
(gtp/make-clear-board-command #(ghost/clear-board))
(gtp/make-genmove-command #(ghost/genmove (first %1)))

(defn -main [& args] (gtp/console))
