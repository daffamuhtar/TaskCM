import SwiftUI
import shared

struct ContentView: View {
    
//    @StateObject var viewModel = HomeViewModel()
    
    @State private var tapCount = UserDefaults.standard.integer(forKey: "Tap")
    
    var body: some View {
        
//        Button ("tapCount : \(tapCount)"){
//            tapCount += 1
//            UserDefaults.standard.set(tapCount, forKey: "Tap")
//        }
        ComposeView()
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
