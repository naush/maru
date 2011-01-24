(ns maru.common.utility.core
	(:require [maru.common.game.color.core :as color])
  (:require [clojure.contrib.string :only [replace-str] :as string]))

(defn read-char-as-string [string index] (str (.charAt string index)))

(defn split-string-at [string index] (map #(apply str %) (split-at (inc index) string)))

(defn remove-string-upto [string index] (apply str (drop index string)))

(defn even-terms [ls] (take-nth 2 (rest ls)))

(defn insert-last [ls elm] (concat ls (list elm)))

(def letter-digit { \A 0  \B 1  \C 2  \D 3  \E 4  \F 5  \G 6  \H 7 \J 8
	                  \K 9  \L 10 \M 11 \N 12 \O 13 \P 14 \Q 15 \R 16
	                  \S 17 \T 18 \U 19 \V 20 \W 21 \X 22 \Y 23 \Z 24 })

(defn letter-to-digit [a] (get letter-digit (Character/toUpperCase a)))

(defn digit-to-letter [d] (first (filter #(= d (get letter-digit %)) (keys letter-digit))))

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
