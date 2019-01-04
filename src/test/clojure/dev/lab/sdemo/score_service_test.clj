(ns dev.lab.sdemo.score-service-test
  (:use [clojure.test])
  (:require [clojure.spec.alpha :as spec]
            [dev.lab.sdemo.util :as util]
            [clojure.spec.gen.alpha :as g]
            [clojure.spec.test.alpha :as t]
            [clojure.test.check.clojure-test :as ct]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen])
  (:import [dev.lab.sdemo Person ScoreService]
           [java.text SimpleDateFormat]))


(spec/def :person/fname string?)
(spec/def :person/lname string?)
(spec/def :person/birthDate
  (spec/with-gen
    inst?
    (fn [] (spec/gen (spec/inst-in #inst "1800-01-01" #inst "2100-01-01")))))

(spec/def :addresspec/roadName string?)
(spec/def :addresspec/houseNo string?)
(spec/def :addresspec/postCode int?)
(spec/def :addresspec/city string?)
(spec/def :addresspec/country string?)

(spec/def ::address (spec/keys :req-un [:addresspec/roadName
                                  :addresspec/houseNo
                                  :addresspec/postCode
                                  :addresspec/city
                                  :addresspec/country]))

(spec/def ::person (spec/keys :req-un [:person/fname
                                 :person/lname
                                 :person/birthDate]
                        :opt-un [::address]))

(spec/def ::scoreType #{"HIGH" "LOW" "MEDIUM"})
(spec/def :score/value int?)
(spec/def ::score (spec/keys :req-un [::scoreType]))

(spec/fdef get-score
        :args (spec/cat :v ::person)
        :ret ::score)


(defn get-score [v]
  (->> (util/to-jtype Person v)
       (.getScore (ScoreService.))
       (util/from-jtype)))


(ct/defspec get-score-test
            100
            (prop/for-all [v (spec/gen ::person)]
                          (println v)
                          (let [w (get-score v)]
                            (not= nil? w))))
