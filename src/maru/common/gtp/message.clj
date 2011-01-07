(ns maru.common.gtp.message)

(def error-unimplemented "? unimplemented\n")
(def error-syntax "? syntax error\n")
(def error-not-found "? command not found\n")
(defn response [output] (str "=" (if (empty? output) "" " ") output "\n"))
