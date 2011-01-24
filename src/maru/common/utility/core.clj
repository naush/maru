(ns maru.common.utility.core
	(:require [maru.common.game.color.core :as color])
  (:require [clojure.contrib.string :only [replace-str] :as string]))

(defn read-char-as-string [string index] (str (.charAt string index)))

(defn split-string-at [string index] (map #(apply str %) (split-at (inc index) string)))

(defn remove-string-upto [string index] (apply str (drop index string)))

(defn even-terms [ls] (take-nth 2 (rest ls)))

(defn insert-last [ls elm] (concat ls (list elm)))

; ToDo: Fix the I/J bug

(defn letter-to-digit [a] (- (int (Character/toUpperCase a)) 65))

(defn digit-to-letter [d] (char (+ 65 d)))

(defn string-to-digit [s] (Integer/parseInt s))

(defn point-from-string [string size]
  (let [x (letter-to-digit (first string))
        y (string-to-digit (remove-string-upto string 1))]
  (+ x (* (- size y) size))))

(defn string-from-point [point size]
  (let [x (rem point size)
        y (- (dec size) (int (/ point size)))]
  (str (digit-to-letter x) (inc y))))

(defn string-to-color [string]
  (cond
    (or (= "b" string) (= "B" string)) color/black
    (or (= "w" string) (= "W" string)) color/white
    :else color/open))
