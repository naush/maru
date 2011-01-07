(ns maru.test.common.utility.core
  (:use [maru.common.utility.core] :reload)
  (:use [clojure.test]))

(deftest test-even-terms
  (is (= (list 2 4) (even-terms (list 1 2 3 4 5)))))

(deftest test-split-string-at-position-one
  (is (= (list "ab" "c") (split-string-at "abc" 1))))

(deftest test-insert-element-at-position-last
  (is (= (list "a" "b" "c") (insert-last (list "a" "b") "c"))))

(deftest test-remove-string-upto-index
  (is (= "bc" (remove-string-upto "abc" 1))))

(deftest convert-char-to-digit
  (is (= 7 (char-to-digit (nth "H12" 0)))))

(deftest convert-string-to-integer
  (is (= 12 (string-to-integer "12"))))
