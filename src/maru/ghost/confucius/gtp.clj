(ns maru.ghost.confucius.gtp
  (:require [maru.ghost.confucius.core :as ghost] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:require [clojure.contrib.string :as string])
  (:require [maru.common.sgf.core :as sgf])
  (:gen-class))

(gtp/make-name-command #(ghost/name))
(gtp/make-version-command #(ghost/version))
(gtp/make-komi-command #(ghost/komi (first %)))
(gtp/make-boardsize-command #(ghost/boardsize (first %)))
(gtp/make-play-command #(ghost/play (first %) (second %)))
(gtp/make-clear-board-command #(ghost/clear-board))
(gtp/make-genmove-command #(ghost/genmove (first %)))

(defn -main [& args] (gtp/console))
