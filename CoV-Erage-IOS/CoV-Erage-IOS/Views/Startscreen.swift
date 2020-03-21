//
//  Startscreen.swift
//  CoV-Erage-IOS
//
//  Created by Jonathan Hasenburg on 21.03.20.
//  Copyright © 2020 Mobile Corona Computing. All rights reserved.
//

import SwiftUI

struct Startscreen: View {
        
    let foregroundColor = Color(red: 0.918, green: 0.949, blue: 0.937, opacity: 1.0)
    
    @ObservedObject var startscreenVM = StartscreenVM()
    @State private var showModal: Bool = false
    @State private var showAlert: Bool = false
    
    var body: some View {
        VStack {
            
            Spacer()
            
            Text("CoV-erage")
                .font(.custom("Arial", size: 80))
                .fixedSize()
                .padding(.vertical, 20.0)
            
            Statistic(labelText: "Gesamtzahl an Einreichungen", value: "100")
            Statistic(labelText: "Deine Einreichungen", value: "\(self.startscreenVM.numberOfSubmittedDroplets)")
                        
            Spacer()
            
            HStack(alignment: .center) {
                Text("Letzte Einreichung vor: ")
                    .padding()
                Spacer()
                Text(self.startscreenVM.displayedLastSubmitted)
                    .font(.largeTitle)
                    .foregroundColor(Color.green)
                Spacer()
            }
            
            Button("Neue Kurzumfrage") {
                if self.startscreenVM.readyToSubmitAgain() {
                    self.showModal = true
                } else {
                    self.showAlert = true
                }
            }
            .padding(EdgeInsets(top: 12, leading: 100, bottom: 12, trailing: 100))
            .frame(maxWidth: .infinity)
            .foregroundColor(Color.white)
            .background(Color(red: 46/255, green: 204/255, blue: 113/255))
            .cornerRadius(10)
            .padding(.bottom)
        }
        .background(Color(red: 0.569, green: 0.184, blue: 0.337, opacity: 1.0))
        .edgesIgnoringSafeArea(.all)
        .foregroundColor(foregroundColor)
        .sheet(isPresented: self.$showModal) {
            PostDropletView(isPresented: self.$showModal, startscreenVM: self.startscreenVM)
         }
        .alert(isPresented: self.$showAlert) {
            Alert(title: Text("Nicht so eilig!"), message: Text("Eine Umfrage jede Minute reicht, danke für deine Mithilfe :)!"), dismissButton: .default(Text("Gerne!")))
        }
    }
}



struct Startscreen_Previews: PreviewProvider {
    static var previews: some View {
        Startscreen()
    }
}

struct Statistic: View {
    
    let labelText: String
    let value: String
    
    var body: some View {
        HStack {
            Text(labelText)
                .font(.headline)
            Spacer()
            Text(value)
                .font(.title)
        }.padding(.horizontal)
    }
}
