(ns maru.common.gtp.command.unit
  (:refer-clojure :exclude [name])
  (:use [maru.common.gtp.core] :reload)
  (:use [maru.common.gtp.command.core] :reload)
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
  (make-version-command #(hash-map :message "0.4"))
  (is (= "= 0.4\n" (execute "version"))))

(deftest execute-by-function-name-with-args
  (make-boardsize-command #(hash-map :message (str "boardsize " (first %))))
  (is (= "= boardsize 1\n" (execute "boardsize" "1"))))

(deftest execute-by-function-name-with-default-implementation
  (is (= "= 2\n" (execute "protocol_version"))))
