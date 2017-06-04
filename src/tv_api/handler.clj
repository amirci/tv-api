(ns tv-api.handler
  (:require 
    [compojure.api.sweet :refer :all]
    [ring.util.http-response :refer :all]
    [schema.core :as s]
    [tv-api.db :as db]
    [tv-lib.core :as lib]))

(def Rating (s/constrained s/Int #(< 0 % 10)))

(def AgeRating (s/enum :Y :Y7 :G :PG :14 :MA))

(s/defschema Origin 
  {:country (s/enum :US :CA)})

(s/defschema TvShow
  {:name (describe s/Str "Name of the show")
   (s/optional-key :description) (describe s/Str "Summary of what the show is about")
   :tvdbid (describe s/Int "Show id from theTVdb.com")
   :poster (describe s/Str "Link to the poster")
   :site_rating (describe Rating "Site Rating of the show from 1 to 10 stars")
   :rating (describe AgeRating "Age rating recomendation for the show")
   :is_family (describe s/Bool "Indicates whether the show is a family show")
   :origin (describe Origin "Country where the show is from")})

(defn- add-family-check
  [show]
  (assoc show :is_family (lib/family-show? show)))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Tv-api"
                    :description "Tv shows API demo"}
             :tags [{:name "shows", :description "APIs about TV shows"}]}}}

    (context "/api" []
      :tags ["shows"]

      (GET "/tv-shows" []
        :return {:shows [TvShow]}
        :summary "Returns the list of tv shows"
        (as-> db/tv-shows s
          (map add-family-check s)
          (assoc {} :shows s)
          (ok s)
          (header s "Access-Control-Allow-Origin" "*"))))))

