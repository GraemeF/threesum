(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `sum?`"
       (fact "1 and 2 can sum"
             (sum? 1 2) => true
             (sum? 2 1) => true)
       (fact "pairs of no less than 3 can sum"
             (sum? 3 3) => true
             (sum? 6 6) => true)
       (fact "pairs of 1 or 2 can't sum"
             (sum? 1 1) => false
             (sum? 2 2) => false)
       (fact "different numbers can't sum"
             (sum? 3 6) => false
             (sum? 6 12) => false))

(facts "about `sum`"
       (fact "produces sum"
             (sum 1 2) => 3))

(facts "about `shift`"
       (fact "moves into empty cell"
             (shift [nil 1 nil nil]) => [1 nil nil nil]
             (shift [nil nil nil 1]) => [nil nil 1 nil]))
