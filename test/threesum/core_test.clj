(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `merge?`"
       (fact "1 and 2 can merge"
             (merge? 1 2) => true
             (merge? 2 1) => true)
       (fact "pairs of no less than 3 can merge"
             (merge? 3 3) => true
             (merge? 6 6) => true
             (merge? 1 1) => false
             (merge? 2 2) => false))
