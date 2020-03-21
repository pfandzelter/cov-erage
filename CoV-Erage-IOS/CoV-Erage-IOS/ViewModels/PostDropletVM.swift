//
//  CreateDropletVM.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

class PostDropletVM: ObservableObject {
    
    let userId: String = "\(UUID())"
    var postalCode: String = ""
    var gender: Int = -1
    var yearOfBirth: String = ""
    @Published var generalHealth: Int = 5
    @Published var coronaVirus: Int = -1
    @Published var numberOfContacts: Double = -1
    @Published var coughing: Int = -1
    @Published var temperature: Int = -1
    @Published var headache: Int = -1
    @Published var soreThroat: Int = -1
    @Published var runnyNose: Int = -1
    @Published var limbPain: Int = -1
    @Published var diarrhea: Int = -1
    @Published var loneliness: Int = -1
    @Published var insomnia: Int = -1
            
    private let webservice: Webservice
    
    init() {
        self.webservice = Webservice()
    }
    
    func postDroplet() {
        
        var dYearOfBirth: Int = -1
        if yearOfBirth != "" {
            dYearOfBirth = Int(yearOfBirth) ?? -1
        }
        
        var dNumberOfContacts: Int = -1
        if numberOfContacts != -1.0 {
            dNumberOfContacts = Int(numberOfContacts)
        }
        
        let droplet = Droplet(userId: userId, postalCode: postalCode, gender: gender, yearOfBirth: dYearOfBirth, generalHealth: generalHealth, coronaVirus: coronaVirus, numberOfContacts: dNumberOfContacts, coughing: coughing, temperature: temperature, headache: headache, soreThroat: soreThroat, runnyNose: runnyNose, limbPain: limbPain, diarrhea: diarrhea, loneliness: loneliness, insomnia: insomnia)
        
        self.webservice.postDroplet(droplet: droplet, completion: { response in
            if (response?.statusCode == 200) {
                print("Posting the droplet succeded")
            } else {
                print("Posting the droplet failed, response is \(String(describing: response))")
            }
        })
    }
    
}
