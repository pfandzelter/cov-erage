//
//  Webservice.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 20.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import Foundation

class Webservice {
    
    func postDroplet(droplet: Droplet, completion: @escaping (PostDropletResponse?) -> ()) {
        
        guard let url = URL(string: "https://aiulvempz3.execute-api.eu-central-1.amazonaws.com/production/droplet") else {
            fatalError("Invalid URL")
        }
        
        let droplet = try? JSONEncoder().encode(droplet)
                
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = droplet
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            
            guard let data = data, error == nil else {
                DispatchQueue.main.async {
                    completion(nil)
                }
                return
            }
            
            let postDropletResponse = try? JSONDecoder().decode(PostDropletResponse.self, from: data)
            DispatchQueue.main.async {
                 completion(postDropletResponse)
            }
           
        }.resume()
        
    }
    
}
