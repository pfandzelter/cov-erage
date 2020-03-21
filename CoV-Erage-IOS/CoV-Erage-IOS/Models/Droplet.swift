//
//  Droplet.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

struct Droplet: Encodable {
    
    let userId: String
    let postalCode: String
    let gender: Int
    let yearOfBirth: Int
    let generalHealth: Int
    let coronaVirus: Int
    let numberOfContacts: Int
    let coughing: Int
    let temperature: Int
    let headache: Int
    let soreThroat: Int
    let runnyNose: Int
    let limbPain: Int
    let diarrhea: Int
    let loneliness: Int
    let insomnia: Int

}
