//
//  PostDropletResponse.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

struct PostDropletResponse: Decodable {
    let statusCode: Int
    let body: String?
}
