(ns maru.common.gtp.core
  (:refer-clojure :exclude [name])
  (:use [clojure.contrib.def :only [defmacro-]])
  (:use [clojure.string :only [split]])
  (:require [maru.common.gtp.message :as message])
  (:require [maru.common.gtp.command :as command]))

(defmacro- make-command [name block]
  `(defn ~(symbol name) [& args#]
    (if args# (~block args#) (~block))))

(defn make-boardsize-command [block] (make-command "boardsize" block))
(defn make-clear-board-command [block] (make-command "clear_board" block))
(defn make-play-command [block] (make-command "play" block))
(defn make-genmove-command [block] (make-command "genmove" block))
(defn make-komi-command [block] (make-command "komi" block))
(defn make-name-command [block] (make-command "name" block))
(defn make-version-command [block] (make-command "version" block))

(defn quit [] (str "quit"))
(defn protocol_version [] "2")
(defn list_commands [] (reduce #(str %1 "\n" %2) command/names))
(defn known_command [command] (.contains command/names command))

(defn parse [input]
  (let [args (split input #" ")]
    (let [name (first args)]
    (if (command/valid {:name name :count (.size (rest args))})
      (command/execute name (rest args))
      message/error-not-found))))

(defn console []
  (loop [output (read-line)]
    (println (parse output))
    (if (= output "quit")
      (System/exit 0)
      (recur (read-line)))))
