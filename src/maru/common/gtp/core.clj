(ns maru.common.gtp.core
  (:refer-clojure :exclude [name])
  (:use [clojure.contrib.def :only [defmacro-]])
  (:use [clojure.string :only [split]])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.gtp.message.core :as message])
  (:require [maru.common.gtp.command.core :as command]))

(defmacro- make-command [name block]
  `(defn ~(symbol name) [& args#] (~block args#)))

(defn make-boardsize-command [block] (make-command "boardsize" block))
(defn make-clear-board-command [block] (make-command "clear_board" block))
(defn make-play-command [block] (make-command "play" block))
(defn make-genmove-command [block] (make-command "genmove" block))
(defn make-komi-command [block] (make-command "komi" block))
(defn make-name-command [block] (make-command "name" block))
(defn make-version-command [block] (make-command "version" block))

(defn quit [state] (assoc state :message "quit"))
(defn protocol_version [state] (assoc state :message "2"))
(defn list_commands [state] (assoc state :message (reduce #(str %1 "\n" %2) command/names)))
(defn known_command [state command] (assoc state :message (.contains command/names command)))

(defn parse [input state]
  (let [args (split input #" ")]
    (let [name (first args)]
    (if (command/valid {:name name :count (.size (rest args))})
      (command/execute name (cons state (rest args)))
      (hash-map :message message/error-not-found)))))

(defn console []
  (loop [state (parse (read-line) state/default)]
    (println (:message state))
    (if (= (:message state) "= quit\n")
      (System/exit 0)
      (recur (parse (read-line) state)))))
