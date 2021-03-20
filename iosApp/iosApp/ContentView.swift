import SwiftUI
import shared


// Sorry I am to stupid to set up a proper Swift UIKit project ¯\_(ツ)_/¯
struct DemoView: UIViewRepresentable {
    func makeUIView(context: Context) -> UIView {
        Demo().alignDemo(kontext: Kontext()).getView()
    }

    func updateUIView(_ uiView: UIView, context: Context) {
    }
}


struct ContentView: View {
    var body: some View {
        DemoView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
