(ns maru.common.utility.core
  (:require [clojure.contrib.string :only [replace-str] :as string]))

(defn read-char-as-string [string index] (str (.charAt string index)))

(defn split-string-at [string index] (map #(apply str %1) (split-at (inc index) string)))

(defn remove-string-upto [string index] (apply str (drop index string)))

(defn strip-newlines [source] (string/replace-str "\n" "" source))

(defn even-terms [ls] (take-nth 2 (rest ls)))

(defn insert-last [ls elm] (concat ls (list elm)))

(defn letter-to-digit [ch] (- (int ch) 65))

(defn digit-to-letter [d] (char (+ 65 d)))

(defn string-to-digit [s] (Integer/parseInt s))

(defn !contains [container element] (not (.contains container element)))
