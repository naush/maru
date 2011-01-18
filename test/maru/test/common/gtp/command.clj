(ns maru.test.common.gtp.command
  (:refer-clojure :exclude [name])
  (:use [maru.common.gtp.core] :reload)
  (:use [maru.common.gtp.command] :reload)
  (:use [clojure.test]))

(deftest match-command-match
  (is (= true (match {:name "quit" :count 0} {:name "quit" :count 0}))))

(deftest match-command-unmatch
  (is (= false (match {:name "quit" :count 0} {:name "known_command" :count 1}))))

(deftest validate-command-with-valid
  (is (= true (valid {:name "quit" :count 0}))))

(deftest validate-command-with-invalid
  (is (= false (valid {:name "quit" :count 1}))))

(deftest execute-by-function-name-with-no-args
  (make-version-command #(str "0.4"))
  (is (= "= 0.4\n" (execute "version"))))

(deftest execute-by-function-name-with-args
  (make-boardsize-command #(str "boardsize " (first %)))
  (is (= "= boardsize 1\n" (execute "boardsize" "1"))))

(deftest execute-by-function-name-with-default-implementation
  (is (= "= 2\n" (execute "protocol_version"))))
