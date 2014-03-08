(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `squashable?`"
       (fact "first nil is always squashable"
             (squashable? nil :a) => true)
       (fact "second nil is not squashable"
             (squashable? :a nil) => false)
       (fact "1 and 2 can squash"
             (squashable? 1 2) => true
             (squashable? 2 1) => true)
       (fact "pairs of no less than 3 can squash"
             (squashable? 3 3) => true
             (squashable? 6 6) => true)
       (fact "pairs of 1 or 2 can't squash"
             (squashable? 1 1) => false
             (squashable? 2 2) => false)
       (fact "different numbers can't squash"
             (squashable? 3 6) => false
             (squashable? 6 12) => false))

(facts "about `squash`"
       (fact "produces sum"
             (squash 1 2) => 3)
       (fact "replaces nil with number"
             (squash nil 5) => 5)
       (fact "squashes nils together"
             (squash nil nil) => nil))

(facts "about `shift`"
       (fact "squashes first squashable pair of cells"
             (shift [:a :b :c :d] :next #(= [:a :b] [%1 %2]) (constantly :ab)) => [:ab :c :d :next]
             (shift [:a :b :c :d] :next #(= [:b :c] [%1 %2]) (constantly :bc)) => [:a :bc :d :next])
       (fact "replaces nil in last cell"
             (shift [:a :b :c nil] :next (constantly false) (constantly nil)) => [:a :b :c :next])
       (fact "does not squash if there are no squashable pairs"
             (shift [:a :b :c :d] :next (constantly false) (constantly nil)) => [:a :b :c :d]))
