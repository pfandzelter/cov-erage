//
//  Droplet.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

struct Droplet: Encodable {
    
    let postalCode: Int
    let yearOfBirth: Int
    let gender: String
    let healthState: Int
    let temperature: Double
    let coughing: Int

}
