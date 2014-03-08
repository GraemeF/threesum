(ns threesum.core)

(def board [[nil nil  3  nil]
            [ 3   2  nil nil]
            [nil  1   3   3 ]
            [ 1  nil  2   1 ]])

(defn format-row [row]
  (map #(if (nil? %)
          "    "
          (format "%04d" %))
       row))

(defn print-board [board]
  (doseq [row board]
    (println (format-row row))))

(defn sum? [a b]
  (or (and (> a 2) (= a b))
      (= 3 (+ a b))))

(def sum +)

(defn shift [row]
  (rest (conj row nil)))

;;(print-board board)

(defn -main []
  (print-board board))
