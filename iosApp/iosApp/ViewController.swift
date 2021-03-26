import UIKit
import shared

class ViewController: UIViewController {

    var headerView: UIView!
    var titleLabel: UILabel!

    var contentView: UIView? = nil

    override func viewDidLoad() {
        super.viewDidLoad()
        setupHeaderAndTitleLabel()
        self.view.backgroundColor = UIColor.white

        let demo = Demo(kontext: Kontext(), display: { kView in
            self.setContentView(view: kView.getView())
        })

        demo.showMainMenu()
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
        view.topAnchor.constraint(equalTo: headerView.bottomAnchor).isActive = true
    }


    func setupHeaderAndTitleLabel() {
        headerView = UIView()
        headerView.backgroundColor = .red
        self.view.addSubview(headerView)

        titleLabel = UILabel()
        titleLabel.text = "Komponents Demo"
        titleLabel.textAlignment = .center
        titleLabel.font = UIFont(name: titleLabel.font.fontName, size: 20)
        headerView.addSubview(titleLabel)

        headerView.translatesAutoresizingMaskIntoConstraints = false
        headerView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor).isActive = true
        headerView.topAnchor.constraint(equalTo: self.view.topAnchor).isActive = true
        headerView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 1).isActive = true
        headerView.heightAnchor.constraint(equalTo: self.view.heightAnchor, multiplier: 0.1).isActive = true

        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.centerXAnchor.constraint(equalTo: headerView.centerXAnchor).isActive = true
        titleLabel.bottomAnchor.constraint(equalTo: headerView.bottomAnchor).isActive = true
        titleLabel.widthAnchor.constraint(equalTo: headerView.widthAnchor, multiplier: 0.4).isActive = true
        titleLabel.heightAnchor.constraint(equalTo: headerView.heightAnchor, multiplier: 0.5).isActive = true
    }

}