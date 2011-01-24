(ns maru.ghost.confucius.gtp.core
  (:require [maru.ghost.confucius.core :as ghost] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:require [clojure.contrib.string :as string])
  (:require [maru.common.sgf.core :as sgf])
  (:gen-class))

(gtp/make-name-command #(ghost/name %))
(gtp/make-version-command #(ghost/version %))
(gtp/make-komi-command #(ghost/komi (first %) (second %)))
(gtp/make-boardsize-command #(ghost/boardsize (first %) (second %)))
(gtp/make-play-command #(ghost/play (first %) (second %) (nth % 2)))
(gtp/make-clear-board-command #(ghost/clear-board %))
(gtp/make-genmove-command #(ghost/genmove (first %) (second %)))

(defn -main [& args] (gtp/console))
