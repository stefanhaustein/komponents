import UIKit
import shared


class ViewController: UIViewController {

    var navigationBar: UINavigationBar!
    var contentView: UIView? = nil
    var demo: Demo!

    override func viewDidLoad() {
        super.viewDidLoad()

 navigationBar = MyNavigationBar()
        self.view.addSubview(navigationBar)

        navigationBar.backgroundColor = UIColor.green
        //navigationBar.frame = CGRect(x:0, y:0, width:self.view.frame.size.width, height:44)

        navigationBar.delegate = self

        navigationBar.translatesAutoresizingMaskIntoConstraints = false
        navigationBar.topAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.topAnchor).isActive = true
        navigationBar.leadingAnchor.constraint(equalTo: self.view.leadingAnchor).isActive = true
        navigationBar.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 1).isActive = true
        self.view.backgroundColor = UIColor.white

        demo = Demo(kontext: Kontext(), select: { selector, kView in
            let navItem = UINavigationItem(title: selector.title)
            self.navigationBar.pushItem(navItem, animated: false)
            self.setContentView(view: kView.getView())
        })

        showMenu()
    }

    func showMenu() {
        let navItem = UINavigationItem(title: "Komponents Demo")
        navItem.backButtonTitle = "Menu"
        navigationBar.items = [navItem]
        setContentView(view: demo.renderMenu().getView())
    }


    func setContentView(view: UIView) {
        if (self.contentView != nil) {
            self.contentView!.removeFromSuperview()
        }
        self.contentView = view
        self.view.addSubview(view)

        view.translatesAutoresizingMaskIntoConstraints = false
        view.leadingAnchor.constraint(equalTo: self.view.leadingAnchor).isActive = true
        view.trailingAnchor.constraint(equalTo: self.view.trailingAnchor).isActive = true
        view.bottomAnchor.constraint(equalTo: self.view.layoutMarginsGuide.bottomAnchor).isActive = true
        view.topAnchor.constraint(equalTo: navigationBar.bottomAnchor, constant: 1).isActive = true
    }

}

extension ViewController:UINavigationBarDelegate{
    func navigationBar(_ navigationBar: UINavigationBar, didPop: UINavigationItem) {
         showMenu()
    }
}


class MyNavigationBar : UINavigationBar {
    override func popItem(animated: Bool) -> UINavigationItem? {
        return super.popItem(animated: false)
    }

}