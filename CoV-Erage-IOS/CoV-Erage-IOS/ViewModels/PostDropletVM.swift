//
//  CreateDropletVM.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

class PostDropletVM: ObservableObject {
    
    var currentPostalCode: Int = 10587
    var currentYearOfBirth: Int = 1990
    var currentGender: String = ""
    @Published var currentHealthState: Int = 4
    @Published var currentTemperature: Double = 36.5
    @Published var currentCoughing: Int = 2
        
    private let webservice: Webservice
    
    init() {
        self.webservice = Webservice()
    }
    
    func postDroplet() {
        let droplet = Droplet(postalCode: currentPostalCode, yearOfBirth: currentYearOfBirth, gender: currentGender, healthState: currentHealthState, temperature: currentTemperature, coughing: currentCoughing)
        
        self.webservice.postDroplet(droplet: droplet, completion: { response in
            if (response?.statusCode == 200) {
                print("Posting the droplet succeded")
            } else {
                print("Posting the droplet failed, response is \(String(describing: response))")
            }
        })
    }
    
}
